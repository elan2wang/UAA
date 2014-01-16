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

/**
 * @author wangjian
 * @create 2013年7月30日 下午8:02:34
 * 
 */
enum JsonScope {
    EMPTY_ARRAY,
    
    NONEMPTY_ARRAY,
    
    EMPTY_OBJECT,
    
    DANGLING_NAME,
    
    NONEMPTY_OBJECT,
    
    EMPTY_DOCUMENT,
    
    NONEMPTY_DOCUMENT,
    
    CLOSED
}
