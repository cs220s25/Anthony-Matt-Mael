#!/bin/sh
echo "Running Checkstyle before pushing..."
mvn --batch-mode checkstyle:check
checkstyle_exit_code=$?
if [ $? -ne 0 ]; then
  echo "Checkstyle violations found. Push CANCELED."
  exit 1
fi
echo "Checkstyle passed. Push ALLOWED."
exit 0