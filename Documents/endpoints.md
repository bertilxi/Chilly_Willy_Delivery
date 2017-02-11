
# Endpoints

## Metadata

### GET /flavors

#### body
```javascript
{}
```

#### response
```javascript
status 200
body: Array<Flavor> 

interface Flavor{
  label: string;
  imgURL: string;
}
```

### GET /containers

#### body
```javascript
{}
```

#### response
```javascript
status 200
body: Array<ContainerType>

interface ContainerType{
  label: string;
  maxFlavors: number;
  variableQuantityOfFlavors: boolean;
}
```

### GET /addins

#### body
```javascript
{}
```

#### response
```javascript
status 200
body: Array<Addin>

interface Addin{
  label: string;
  imgURL: string;
}
```

### GET /sauces

#### body
```javascript
{}
```

#### response
```javascript
status 200
body: Array<Sauce>

interface Sauce{
  label: string;
  imgURL: string;
}
```

## Core

### POST /session/:deviceID

open the session and identify the device with the server
TODO: implement a Logged user strategy

#### body
```javascript
{}
```
#### response

```javascript
status 200
body deviceID
```


## Order

### POST /order

Send new order to the server

#### body
```javascript
interface Order {
  _id: number;
  containerType: ContainerType;
  flavors: Array<Flavor>;
  sauce: Sauce;
  addins: Array<Addin>;
  quatity: number;
  delivered: boolean; // false by default
}
```

#### response

```javascript
status 200
body orderID
```

### GET /orders/:deviceID

Get all the not delivered orders by the deviceID

#### body
```javascript
{}
```

#### response

```javascript
status 200
body Array<Order>

interface Order {
  _id: number;
  containerType: ContainerType;
  flavors: Array<Flavor>;
  sauce: Sauce;
  addins: Array<Addin>;
  quatity: number;
  delivered: boolean;
}
```


## Location

### GET /location/:orderID

Get the location of an order

#### body
```javascript
{}
```

#### response

```javascript
status 200
body LatLng

interface LatLng {
  latitude: number;
  longitude: number;
}
```


## Review

### POST /review

Send a review to the server

#### body
```javascript
interface Review { 
  rating: Number;
  img: String; // base64 image string
  comment: String;
}
```

#### response

```javascript
status 200
body reviewID
```



