KTFILEINCOMD="";
for ktFile in $(ls):
do
    EXT=${ktFile#*.}
    if [ $EXT = 'kt' ]
    then
        KTFILEINCOMD+=$ktFile" "
    fi
done

#Execute the compile command
kotlinc ${KTFILEINCOMD} -include-runtime -d .jar/main.jar && java -jar .jar/main.jar