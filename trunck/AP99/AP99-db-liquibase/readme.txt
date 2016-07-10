#export all db objects
>mvn liquibase:generateChangeLog -DsrcSchema=peakre_init_dev -DchangeLogFile=diff.xml

#deploy upgrade-db.xml to unicorn_init_tst
>mvn liquibase:update2 -DpropertyFile=unicorn/unicorn_init_tst.properties -DchangeLogFile=../target/dbscript/upgrade-db.xml

#deploy upgrade-db.xml to unicorn_local
>mvn liquibase:update2 -DpropertyFile=peakre/dev.properties -DchangeLogFile=../AP99-claim/AP99-claim-runtime/src/main/dbscript/initdata/index.xml

#compare tables unicorn_init_tst with unicorn_dev
>mvn liquibase:diff -DsrcSchema=unicorn_init_tst -DdstSchema=unicorn_dev -DchangeLogFile=diff.xml -DdiffTypes=tables

#export data by initdata.properties 
>mvn package -Dexport=initdata -DsrcSchema=peakre_init_dev

#single Table Structure export
>mvn package -Dexport=table -Dtable=T_SAMPLE_USER -DsrcSchema=peakre_init_dev -DexportType=

#batch Tables Structure export
>mvn package -Dexport=table -DsrcSchema=peakre_init_dev -DexportType=

#-DexportType  optional parameters  List of diff types to include in Change Log expressed as a comma
#separated list from: tables, views,columns, indexes, foreignkeys,primarykeys, uniqueconstraints data.
#If this is null then the default types will be: tables, views, columns, indexes, foreignkeys, primarykeys, uniqueconstraints.
