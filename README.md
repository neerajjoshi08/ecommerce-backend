### Ecommerce backend

This repo will contains the backend code.

[![Gradle Package](https://github.com/neerajjoshi8/ecommerce-backend/actions/workflows/build.yml/badge.svg?branch=main&event=push)](https://github.com/neerajjoshi8/ecommerce-backend/actions/workflows/build.yml)


## Setup
1. Start up the Temporal server and create the respective namespace needed.
    ```bash
    temporal server start-dev
    
    # Run using db file
    temporal server start-dev --db-filename temporal.db 
    ```
2. Up the database server
    ```bash
   docekr start products-service-mysql
    ```
3. Up the spring boot or application server.

