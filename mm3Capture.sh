#/bin/sh
export LOG_DIR=/tmp
export LOG_LEVEL=DEBUG

java -jar Mm3Capture.jar -DLOG_DIR=$LOG_DIR -DLOG_LEVEL=$DEBUG > console.log &
