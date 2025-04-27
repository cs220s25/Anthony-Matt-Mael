#!/bin/sh
echo "Running Checkstyle before pushing..."
mvn --batch-mode checkstyle:check
if [ $? -ne 0 ]; then
  echo "Checkstyle violations found. Push canceled."
  exit 1
fi
echo "Checkstyle passed. Push allowed."
exit 0

