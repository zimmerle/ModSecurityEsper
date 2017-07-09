#!/bin/bash

echo "Quit"
curl -o /dev/stdout "http://localhost:8085/sendevent?stream=QuitEvent"
echo "Done!"


