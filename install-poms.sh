#!/usr/bin/env zsh


_Main() {
  local cmd
  local parentPoms
  local childPoms
  parentPoms=("cli-dependencies.xml")
  childPoms=()

  for pom in "${parentPoms[@]}" ; do
      cmd="./mvnw -f $pom clean install -N"
      echo "Executing: $cmd"
      eval "${cmd}" || exit 1
  done
  for pom in "${childPoms[@]}" ; do
      cmd="./mvnw -f $pom clean install -N"
      echo "Executing: $cmd"
      eval "${cmd}" || exit 1
  done
}

_Main
