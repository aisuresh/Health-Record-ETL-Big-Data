# Health Record ETL Big Data application

Automation pipeline to ingest and calculate data in BigData Platform using Hadoop HDFS, Hive, Spark.

### Prerequisites

```
Download the Hortonworks Sandbox (VirtualBox / Docker) from 
https://www.cloudera.com/downloads/hortonworks-sandbox/hdp.html
Ensure VirtualBox (v6.0) / Docker installed
Please note that a computer with minimum 10 GB RAM dedicated to the VirtualBox / Docker required
```

### Installing

1. Import Hortonworks Sandbox

Open VirtualBox and navigate to File -> Import Appliance. Select the sandbox image you downloaded and click Open.

![](/screenshots/sandbox-hdp-vbox-setting.jpg)

Make sure to allocate at least 10 GB (10240 MB) of RAM for the sandbox.

Click Import and wait for VirtualBox to import the sandbox.

2. Start Hortonworks Sandbox
Once the sandbox has finished being imported, you may start it by selecting the sandbox and clicking “Start” from the VirtualBox menu.

![](/screenshots/sandbox-hdp-vbox-start.jpg)

3. A console window opens and displays the boot process. This process take a few minutes. When you see the following screen, you may begin using the sandbox.

![](/screenshots/sandbox-hdp-vbox-splash-screen.jpg)

4. Map Sandbox IP to your desired hostname in the hosts file. Execute below command in linux shell window.

```
echo '127.0.0.1 sandbox-hdp.hortonworks.com' | sudo tee -a /etc/hosts
```

![](/screenshots/hdp-sandbox-hostname.JPG)

### SSH on to HDP sandbox
ssh root@sandbox-hdp.hortonworks.com -p 2201

Default password is root/hadoop

Please remember to change password after first time login!

![](screenshots/hdp-sandbox-ssh-login.JPG)

Run 'ambari-admin-password-reset' command to reset admin deafult password. (e.g. admin/admin)

### Access Shell Web Client

Alternatively you can access sandbox from shell web client (remember to add hostname sandbox-hdp.hortonworks.com)
http://sandbox-hdp.hortonworks.com:4200/ or http://localhost:4200

![](screenshots/hdp-sandbox-web-console-login.JPG)

The default login using credentials: root / hadoop

Must change the password when you login for first time. The new password used for accesssing sandbox through ssh / web client.

### Access HDP sandbox UI 

Access HDP sandbox Ambari UI through http://sandbox-hdf.hortonworks.com:8080 or http://localhost:8080

![](/screenshots/hdp-sandbox-home-page.JPG)

User credentials:     
raj_ops / raj_ops    
maria_dev / maria_dev    
admin/admin    

### Data Ingestion

SSH to sandbox
ssh root@sandbox-hdp.hortonworks.com -p 2201

![](screenshots/hdp-sandbox-ssh-login.JPG)

#### HDFS

1. download source data file
```
wget https://chronicdata.cdc.gov/views/735e-byxc/rows.csv
```

2. hdfs make directory
```
hdfs dfs -mkdir /assignment
```
3. copy file from local file system to hadoop destination file system
```
hdfs dfs -put rows.csv /assignment
```
4. verify file copied in hadoop
```
hdfs dfs -ls /assignment
```

![](/screenshots/hadoop-data-load.JPG)

#### Hive
1. Create Hive table - It create table in default schema
```
CREATE TABLE IF NOT EXISTS health_record(YearStart INT,YearEnd INT,LocationAbbr STRING,LocationDesc STRING,Datasource STRING,Class STRING,Topic STRING,Question STRING,Data_Value_Unit STRING,Data_Value_Type STRING,Data_Value DOUBLE,Data_Value_Alt DOUBLE,Data_Value_Footnote_Symbol STRING,Data_Value_Footnote STRING,Low_Confidence_Limit DOUBLE,High_Confidence_Limit  DOUBLE,Sample_Size INT,Total STRING,`Age(months)` STRING,Gender STRING,`Race/Ethnicity` STRING,GeoLocation STRING,ClassID STRING,TopicID STRING,QuestionID STRING,DataValueTypeID STRING,LocationID INT,StratificationCategory1 STRING,Stratification1 STRING,StratificationCategoryId1 STRING,StratificationID1 STRING)
COMMENT 'This is health record table'
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde'
tblproperties("skip.header.line.count"="1"); 
```

![](screenshots/hive-table-create.JPG)

2. Desribe table
```
describe health_record;
```
![](screenshots/hive-describe-table.JPG)

3. Load data from HDFS into Hive table 
```
LOAD DATA INPATH '/assignment/rows.csv' OVERWRITE INTO TABLE health_record;
```
![](screenshots/hive-data-load.JPG)

4. Check row count to verify data loaded correctly 
```
select count(*) from health_record;
```
![](screenshots/hive-table-count.JPG)


5. Query to check table data
```
SELECT * FROM health_record limit 5;
```
![](screenshots/hive-table-data.JPG)

We can view hive queries in Hive Query UI

![](screenshots/hive-query-ui.JPG)

### Running Spark application

1. Copy PySpark script `health_avg_calculation.py` to HDP sandbox:
```
scp -p 2222 ./health_avg_calculation.py root@sandbox-hdp.hortonworks.com:/root
```

Execute spark-submit to run the program:
```
spark-submit health_avg_calculation.py \
    --master yarn-client \
    --num-executors 1 \
    --driver-memory 1g \
    --executor-memory 1g \
    --executor-cores 1
```

![](screenshots/spark-job-submit.JPG)

Navigate to Spark UI page to view spark job history:


![](screenshots/spark-ui.JPG)

The calculate average results are persist in Hive.

## REST API

### API to access results stored in Hive

Developed RESTful API service using Spring Boot to access results from Hive

Go to rest-api directory, run `mvn install` to build the project.

Ensure that port (7755) mentioned in src/main/application.properties is available to use.

Once build Once build is successful, it generate restapi-1.0-SNAPSHOT.jar in target directory.

Copy it to anywhere you want, execute java -jar 'path of the restapi jar'. The below command bring up rest api server and listining to 7755 for api requests.

```
java -jar target/restapi-1.0-SNAPSHOT.jar
```

![](screenshots/rest-api-build-run.jpg)

### REST API Request through curl

Open a new terminal window, execute curl command to hit the api

To get list of database:

```
curl http://localhost:7755/api/hive/database
```

To get list of tables:

```
curl http://localhost:7755/api/hive/table
```

To get the average of all age group:

```
curl http://localhost:7755/api/hive/average
```

![](screenshots/rest-api-all-age-average.jpg)

To get the average of female only:

```
curl http://localhost:7755/api/hive/femaleAverage
```

![](screenshots/rest-api-female-average.jpg)


Alternatively access APIs using rest api / browser url to get the API response

```
http://localhost:7755/api/hive/femaleAverage
```

![](screenshots/rest-api-response.jpg)


```
http://localhost:7755/api/hive/average
```

![](screenshots/rest-api-response-average.jpg)

The REST API response contains year, question, average.


