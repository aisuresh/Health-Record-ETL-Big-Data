"""
@author Suresh Rajagopal

Spark application to calculate average from health record.
Source of data from Hive and store result back into Hive.

"""

from os.path import expanduser, join, abspath

from pyspark.sql import SparkSession
from pyspark.sql import Row
from pyspark.sql import functions as func

warehouse_location = abspath('spark-warehouse')

spark = SparkSession \
    .builder \
    .appName("Health_report_average_app") \
    .config("spark.sql.warehouse.dir", warehouse_location) \
    .enableHiveSupport() \
    .getOrCreate()

# Read data from source Hive table health_record
recordDF = spark.sql("SELECT yearstart AS year, question, gender, `age(months)` AS age_group, CAST(data_value as INT) as value FROM health_record")

recordDF.printSchema()
recordDF.show(5)

# Remove header from source dataframe
header = recordDF.first()
withoutHeader = recordDF.rdd.filter(lambda line: line!=header)
healthDF = spark.createDataFrame(withoutHeader)

# Register dataframe as temporary table
healthDF.registerTempTable("health_record")
healthDF.show(5)

# Calcualte Average of question's by year for all age group
health_age_group_avg = healthDF.groupBy("question", "year", "age_group").avg("value").select("question", "year", "age_group", func.col("avg(value)").alias("value"))

# Write result into Hive table
health_age_group_avg.write.mode('overwrite').saveAsTable('result.age_group_average')

#healthDF.groupBy('question', 'year').agg({'value','avg'}).show()

# Calcualte Average of question's by year for female only
female_filterDF = healthDF.filter(healthDF.gender == 'Female')
female_averageDF = female_filterDF.groupBy('question', 'year').avg('value').select("question", "year", func.col("avg(value)").alias("average"))

# Write female average result into Hive table
female_averageDF.write.mode('overwrite').saveAsTable('result.female_average')
