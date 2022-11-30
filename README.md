# REST-API-UniqueStringGenerator
# Unique String Generator

Unique String Generator is a simple REST API which generates unique strings, basing on parameters given by user.

## Installation

App needs connection to MySQL DB, so make sure you have provided one. Once you've created schema in your database, you can use below SQL query to create valid tabel for USG app:

```bash
CREATE TABLE `jinx11_uniqueStringGenerator`. (`id` INT NOT NULL AUTO_INCREMENT , `userChars` VARCHAR(100) NOT NULL , `maxLength` INT NOT NULL , `minLength` INT NOT NULL , `howManyWanted` INT NOT NULL , `status` TINYINT NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;
```

## Usage
To run app use command:

```bash
mvn spring-boot:run
```

App has 3 endpoints:

1. ```/generete``` POST type - starts unique strings generetion. You have to provide parameters for unique strings generation via POST. Given data has to be application/JSON

  
   POST parameters example:
   ```bash
   [
   {
    "userChars": ["t", "s", "y", "f","g", "q"],
    "maxLength": 15,
    "minLength":8,
    "howManyWanted": 10000
   },
   {
    "userChars": ["t", "s", "y", "f","g", "q"],
    "maxLength": 15,
    "minLength":8,
    "howManyWanted": 100000
   },
   {
    "userChars": ["t", "s", "y", "f","g", "q"],
    "maxLength": 15,
    "minLength":8,
    "howManyWanted": 100
   }
   ]
   ```

2. ```/workingJobs``` GET type - returns currently running threads generating results for user request

3. ```/results``` GET type - returns ZIP archive with txt files, containg all finished jobs' results

