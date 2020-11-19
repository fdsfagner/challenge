## Challenge Application

#### Reset state before starting tests

POST /reset

HTTP Status: 200 

Result: OK

#### Get balance for non-existing account

GET /balance?account_id=1234

HTTP Status: 404 

Result: 0

#### Create account with initial balance

POST /event 
```json
{
   "type":"deposit",
   "destination":"100",
   "amount":10
}
```

HTTP Status: 201

Result:
```json 
{
   "destination":{
      "id":"100",
      "balance":10
   }
}
```

#### Deposit into existing account

POST /event 

```json
{
   "type":"deposit",
   "destination":"100",
   "amount":10
}
```

HTTP Status: 201

Result: 
```json
{
   "destination":{
      "id":"100",
      "balance":20
   }
}
```

#### Get balance for existing account

GET /balance?account_id=100

HTTP Status: 200
 
Result: 20

#### Withdraw from non-existing account

POST /event 

```json
{
   "type":"withdraw",
   "origin":"200",
   "amount":10
}
```
HTTP Status: 404 

Result: 0

#### Withdraw from existing account

POST /event 
```json
{
   "type":"withdraw",
   "origin":"100",
   "amount":5
}
```

HTTP Status: 201

Result:
```json
{
   "origin":{
      "id":"100",
      "balance":15
   }
}
```

#### Transfer from existing account

POST /event 
```json
{
   "type":"transfer",
   "origin":"100",
   "amount":15,
   "destination":"300"
}
```

HTTP Status: 201

Result:
```json
{
   "origin":{
      "id":"100",
      "balance":0
   },
   "destination":{
      "id":"300",
      "balance":15
   }
}
```

#### Transfer from non-existing account

POST /event 
```json
{
   "type":"transfer",
   "origin":"200",
   "amount":15,
   "destination":"300"
}
```

HTTP Status: 404

Result: 0

## Hexagonal Architecture

In hexagonal architecture we have the following hexagons:

### Application
In this hexagon there are ports, use cases and queries. These components are used to translate what comes from outside (conductive / driver operations) of the hexagon as what comes from within and leaves the hexagon (directed / driven operations).

### Domain
Classes that represent business entities and contain business-critical algorithms should stay here. It is very important that no class here depends on existing classes in Application and Framework, we do this as a means of practicing DDD and guaranteeing dependency inversion.

### Framework
Here should be the classes that represent the adapters (or controller in MVC terminology) of input and output of the application and other classes within the scope of the framework.
