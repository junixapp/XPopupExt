#!/usr/bin/env sh
./gradlew clean build bintrayUpload -PbintrayUser=li-xiaojun -PbintrayKey=99f6d08934145ab27e99a75af031b0b3c94d99a4 -PdryRun=false
