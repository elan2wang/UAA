/*
 * Copyright (c) Jian Wang.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package org.uaa.common.json;

import java.io.IOException;
import java.io.Writer;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjian
 * @create 2013年7月30日 下午8:00:18
 * 
 */
public class JsonWriter {

	//~ Static Fields ==================================================================================================
	private static final String[] REPLACEMENT_CHARS;
	private static final String[] HTML_SAFE_REPLACEMENT_CHARS;
	private static final DecimalFormat df = new DecimalFormat("#.##");
	private static final SimpleDateFormat datetimeF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
	
	static {
		REPLACEMENT_CHARS = new String[128];
		for (int i = 0; i <= 0x1f; i++) {
			REPLACEMENT_CHARS[i] = String.format("\\u%04x", (int) i);
		}
		REPLACEMENT_CHARS['"'] = "\\\"";
		REPLACEMENT_CHARS['\\'] = "\\\\";
		REPLACEMENT_CHARS['\t'] = "\\t";
		REPLACEMENT_CHARS['\b'] = "\\b";
		REPLACEMENT_CHARS['\n'] = "\\n";
		REPLACEMENT_CHARS['\r'] = "\\r";
		REPLACEMENT_CHARS['\f'] = "\\f";
		HTML_SAFE_REPLACEMENT_CHARS = REPLACEMENT_CHARS.clone();
		HTML_SAFE_REPLACEMENT_CHARS['<'] = "\\u003c";
		HTML_SAFE_REPLACEMENT_CHARS['>'] = "\\u003e";
		//HTML_SAFE_REPLACEMENT_CHARS['&'] = "\\u0026";
		//HTML_SAFE_REPLACEMENT_CHARS['='] = "\\u003d";
		HTML_SAFE_REPLACEMENT_CHARS['\''] = "\\u0027";
	}

	//~ Instance Fields ================================================================================================
	private final List<JsonScope> stack = new ArrayList<JsonScope>();
	{
		stack.add(JsonScope.EMPTY_DOCUMENT);
	}

	private final Writer out;

	private String separator = ": ";

	private boolean htmlSafe;

	private String deferredName;

	private String indent;

	private boolean lenient;

	//~ Constructor ====================================================================================================
	public JsonWriter(Writer out) {
		if (out == null) {
			throw new NullPointerException("out == null");
		}
		this.out = out;
		this.htmlSafe = true;
		this.deferredName = null;
		this.indent = "    ";
	}

	public JsonWriter beginArray() throws IOException{
		writeDeferredName();
		return open(JsonScope.EMPTY_ARRAY, "[");
	}

	public JsonWriter endArray() throws IOException{
		return close(JsonScope.EMPTY_ARRAY, JsonScope.NONEMPTY_ARRAY, "]");
	}

	public JsonWriter beginObject() throws IOException{
		writeDeferredName();
		return open(JsonScope.EMPTY_OBJECT, "{");
	}

	public JsonWriter endObject() throws IOException{
		return close(JsonScope.EMPTY_OBJECT, JsonScope.NONEMPTY_OBJECT, "}");
	}

	public JsonWriter name(String name) {
		if (name==null) {
			throw new NullPointerException("name == null");
		}
		if (deferredName != null) {
			throw new IllegalStateException();
		}
		if (stack.isEmpty()) {
			throw new IllegalStateException("MyJsonWriter is closed.");
		}
		deferredName = name;
		return this;
	}

	public JsonWriter value(Object value) throws IOException{
		if (value == null){
			return nullValue();
		}
		writeDeferredName();
		beforeValue(false);
		if (value instanceof Timestamp) {
			string(datetimeF.format(value));
		} else if (value instanceof java.sql.Date){
			string(dateF.format(value));
		} else if (value instanceof java.util.Date) {
			string(datetimeF.format(value));
		} else if (value instanceof Boolean) {
			out.write((Boolean)value ? "true" : "false");
		} else if (value instanceof Number) {
			if (value instanceof Double  || value instanceof Float) {
				out.write(df.format(value).toString());
			} else {
				out.write(value.toString());
			}
		} else {
			string(value.toString());
		}
		return this;
	}


	public void flush() throws IOException {
		if (stack.isEmpty()) {
			throw new IllegalStateException("JsonWriter is closed.");
		}
		out.flush();
	}

	public void close() throws IOException {
		out.close();

		int size = stack.size();
		if (size > 1 || size == 1 && stack.get(size - 1) != JsonScope.NONEMPTY_DOCUMENT) {
			throw new IOException("Incomplete document");
		}
		stack.clear();
	}

	//~ Private Methods =====================================================================================

	private JsonWriter nullValue() throws IOException {
		if (deferredName != null){
			writeDeferredName();
		}
		beforeValue(false);
		out.write("\"\"");
		return this;
	}

	private void beforeName() throws IOException{
		JsonScope context = peek();
		if (context == JsonScope.NONEMPTY_OBJECT){
			out.write(',');
		} else if (context != JsonScope.EMPTY_OBJECT) {
			throw new IllegalStateException("Nesting problem: " + stack);
		}
		newline();
		replaceTop(JsonScope.DANGLING_NAME);
	}

	private void beforeValue(boolean root) throws IOException {
		switch(peek()){
		case NONEMPTY_DOCUMENT:
			if (!lenient){
				throw new IllegalStateException("JSON must have only one top-level value");
			}

		case EMPTY_DOCUMENT:
			if (!lenient && !root){
				throw new IllegalStateException("JSON must start with an array or an object");
			}
			replaceTop(JsonScope.NONEMPTY_DOCUMENT);
			break;

		case EMPTY_ARRAY:
			replaceTop(JsonScope.NONEMPTY_ARRAY);
			newline();
			break;

		case NONEMPTY_ARRAY:
			out.append(',');
			newline();
			break;

		case DANGLING_NAME:
			out.append(separator);
			replaceTop(JsonScope.NONEMPTY_OBJECT);
			break;

		default:
			throw new IllegalStateException("Nesting problem: " + stack);    
		}
	}

	private JsonWriter close(JsonScope empty, JsonScope nonempty, String closeBracket) throws IOException{
		JsonScope context = peek();
		if (context != nonempty && context != empty) {
			throw new IllegalStateException("Nesting problem: " + stack);
		}
		if (deferredName != null) {
			throw new IllegalStateException("Dangling name: " + deferredName);
		}

		stack.remove(stack.size() - 1);
		if (context == nonempty) {
			newline();
		}
		out.write(closeBracket);
		return this;
	}

	private void writeDeferredName() throws IOException{
		if (deferredName != null){
			beforeName();
			string(deferredName);
			deferredName=null;
		}
	}

	private void newline() throws IOException {
		if (indent == null){
			return;
		}
		out.write("\n");
		for (int i=1;i<stack.size();i++){
			out.write(indent);
		}
	}

	private JsonWriter open(JsonScope empty, String openBracket) throws IOException{
		beforeValue(true);
		stack.add(empty);
		out.write(openBracket);
		return this;
	}

	private JsonScope peek(){
		int size = stack.size();
		if (size==0){
			throw new IllegalStateException("MyJsonWriter is closed.");
		}
		return stack.get(size-1);
	}

	private void replaceTop(JsonScope topOfStack){
		stack.set(stack.size()-1, topOfStack);
	}

	private void string(String value) throws IOException {
		String[] replacements = htmlSafe ? HTML_SAFE_REPLACEMENT_CHARS : REPLACEMENT_CHARS;
		out.write("\"");
		int last =0;
		int length = value.length();
		for (int i=0; i<length; i++){
			char c = value.charAt(i);
			String replacement;
			if (c<128){
				replacement = replacements[c];
				if (replacement == null) {
					continue;
				}
			} else if (c=='\u2028') {
				replacement = "\\u2028";
			} else if (c=='\u2029') {
				replacement = "\\u2029";
			} else {
				continue;
			}
			if (last<i) {
				out.write(value, last, i-last);
			}
			out.write(replacement);
			last = i + 1;
		}
		if (last<length){
			out.write(value, last, length - last);
		}
		out.write("\"");
	}

	//~ Setters and Getters =================================================================================

	public void setIndent(String indent) {
		if (indent.length() == 0){
			this.indent = null;
			this.separator = ":";
		} else {
			this.indent = indent;
			this.separator = ":";
		}
	}

	public void setLenient(boolean lenient) {
		this.lenient = lenient;
	}

	public void setHtmlSafe(boolean htmlSafe) {
		this.htmlSafe = htmlSafe;
	}

	public boolean isHtmlSafe() {
		return htmlSafe;
	}

	public boolean isLenient() {
		return lenient;
	}

}
