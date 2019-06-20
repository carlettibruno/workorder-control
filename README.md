# Work Order Control

Main idea of this project is control a service

## Technologies

- Java 8
- EJB3
- JPA
- REST
- AngularJS
- jQuery
- HTML
- CSS

## Features

### Home / Dashboard
*create some reports here*

### Generate service order
#### Fields to fill
- Internal service order number
- Choose customers
- Email (automatically filled with customer information)
- Service description
- Delivery estimate
- Address
    - Title
    - Address
    - ZipCode
    - Additional info
    - Neighboorhod
    - State
    - City
- Service photos

Creation of service order will generate a terminal printer request. See more in *Thermal Printer* section.

### Employees
### Customers
### Services order
### Flow steps
### Configuration
### Thermal Printer
Thermal Printer is a module that permit check all print requests and then, print the service order.

This is a terminal app that you run to effectively print something.

***Important** do not forget to install the drivers of your thermal printer. This app works great with **Bematech MP 4200 TH**, I didn't test others models*

#### Configuration (config.properties)
*config.properties* must be in same folder of jar file.
- **impressora.ip**: tells to app what is the thermal printer IP
- **impressora.tempo.espera**: how long app will waiting for the next search for printing (in milliseconds)
- **service.getinfoprint.url**: address to get new available request prints
- **service.url**: url to validate user
- **service.user**: user previously created to get available prints
- **service.password**: user password

##### Configuration sample
```
impressora.ip=192.168.1.100
impressora.tempo.espera=4000
service.getinfoprint.url=http://youraddress/contextpath/services/filaimpressora
service.url=http://youraddress/contextpath/services/login
service.user=youruser
service.password=yourpwd
```

#### Run
java -jar printer.jar

### Shipping integration