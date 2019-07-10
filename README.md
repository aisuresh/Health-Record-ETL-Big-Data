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

Make sure to allocate at least 10 GB (10240 MB) of RAM for the sandbox.

Click Import and wait for VirtualBox to import the sandbox.

2. Start Hortonworks Sandbox
Once the sandbox has finished being imported, you may start it by selecting the sandbox and clicking “Start” from the VirtualBox menu.

3. A console window opens and displays the boot process. This process take a few minutes. When you see the following screen, you may begin using the sandbox.

4. Access Ambari UI through sandbox-hdf.hortonworks.com:8080


# SSH on to VirtualBox Virtual Machine
ssh root@sandbox-hdp.hortonworks.com -p 2201

Default password is root/hadoop

Please remember to change password after first time login!

## Running Spark application


## Visualization



