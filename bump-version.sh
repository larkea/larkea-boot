#!/bin/bash -

showhelp() {
  echo "Usage: ./bump-version.sh version."
}

[ -z "$1" ] && showhelp && exit 0

VERSION=$1
echo "${VERSION}" > VERSION
echo "Files modified successfully, version bumped to ${VERSION}"

git commit -a -m "Bumped version number to ${VERSION}"

