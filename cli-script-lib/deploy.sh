#!/usr/bin/env zsh


_Main() {
  local targetDir
  local srcJar

  srcJar="target/cli-script-lib-1.0.0.jar"
  targetDir="$(realpath ~/shell/bin/lib)"
  local cmd="./mvnw clean package && cp $srcJar $targetDir/."
  echo "Executing: $cmd"
  eval "$cmd"
  echo "Done Executing: $cmd"
}

_Main
