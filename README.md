# OhMyBeer-Services

## Synopsis

WebService for app OhMyBeer => https://github.com/firemoon/OhMyBeer

## Installation

To use this project you need to install

- Docker

Use this command to launch couchbase server:

- docker run -d -v /srv/data/couchbase:/opt/couchbase/var -p 8091-8093:8091-8093 -p 11210:11210 couchbase/server

Activiate the beer-sample

- Couchbase with the beer-sample (http://developer.couchbase.com/documentation/server/4.1/getting-started/installing.html#story-h2-6)

You have to change the ip address in Constante.java

- public static final String IP = "172.20.20.29";

## Build

./gradlew build

## Run

java -jar ./build/libs/ohmybeer-*.jar

## License

Copyright (c) 2016 Nicolas Godefroy

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.