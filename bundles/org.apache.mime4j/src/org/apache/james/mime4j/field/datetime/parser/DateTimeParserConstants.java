/* Generated By:JavaCC: Do not edit this line. DateTimeParserConstants.java */
/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/
package org.apache.james.mime4j.field.datetime.parser;

public interface DateTimeParserConstants {

  int EOF = 0;
  int OFFSETDIR = 24;
  int MILITARY_ZONE = 35;
  int WS = 36;
  int COMMENT = 38;
  int DIGITS = 46;
  int QUOTEDPAIR = 47;
  int ANY = 48;

  int DEFAULT = 0;
  int INCOMMENT = 1;
  int NESTED_COMMENT = 2;

  String[] tokenImage = {
    "<EOF>",
    "\"\\r\"",
    "\"\\n\"",
    "\",\"",
    "\"Mon\"",
    "\"Tue\"",
    "\"Wed\"",
    "\"Thu\"",
    "\"Fri\"",
    "\"Sat\"",
    "\"Sun\"",
    "\"Jan\"",
    "\"Feb\"",
    "\"Mar\"",
    "\"Apr\"",
    "\"May\"",
    "\"Jun\"",
    "\"Jul\"",
    "\"Aug\"",
    "\"Sep\"",
    "\"Oct\"",
    "\"Nov\"",
    "\"Dec\"",
    "\":\"",
    "<OFFSETDIR>",
    "\"UT\"",
    "\"GMT\"",
    "\"EST\"",
    "\"EDT\"",
    "\"CST\"",
    "\"CDT\"",
    "\"MST\"",
    "\"MDT\"",
    "\"PST\"",
    "\"PDT\"",
    "<MILITARY_ZONE>",
    "<WS>",
    "\"(\"",
    "\")\"",
    "<token of kind 39>",
    "\"(\"",
    "<token of kind 41>",
    "<token of kind 42>",
    "\"(\"",
    "\")\"",
    "<token of kind 45>",
    "<DIGITS>",
    "<QUOTEDPAIR>",
    "<ANY>",
  };

}
