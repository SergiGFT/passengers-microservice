```mermaid
---
title: Route Management
---
classDiagram
class RouteManagement {
<<Bounded Context>>
+RouteService
+RouteRepository
+RouteFactory
}
class Route {
<<Aggregate Root>>
String id
String name
RouteType type
List~RouteSegment~ segments
List~Schedule~ schedules
List~Vehicle~ assignedVehicles
}
class RouteType {
<<Enumeration>>
LOOP
TWO_WAY
}
class RouteSegment {
<<Entity>>
String id
Stop startStop
Stop endStop
Distance distance
}
class Stop {
<<Entity>>
String id
String name
Address address
Coordinates coordinates
}
class Address {
<<Value Object>>
String street
String city
String postalCode
}
class Coordinates {
<<Value Object>>
Double latitude
Double longitude
}
class Distance {
<<Value Object>>
Double value
DistanceUnit unit
}
class DistanceUnit {
<<Enumeration>>
KILOMETERS
MILES
}
class Schedule {
<<Entity>>
String id
Time startTime
Time endTime
DayType dayType
Frequency frequency
}
class DayType {
<<Enumeration>>
WEEKDAY
WEEKEND
HOLIDAY
}
class Frequency {
<<Value Object>>
Integer value
TimeUnit unit
}
class TimeUnit {
<<Enumeration>>
MINUTES
HOURS
}

    RouteManagement --> Route
    Route --> RouteType
    Route --> "1..*" RouteSegment
    Route --> "1..*" Schedule
    RouteSegment --> "2" Stop
    RouteSegment --> Distance
    Stop --> Address
    Stop --> Coordinates
    Distance --> DistanceUnit
    Schedule --> DayType
    Schedule --> Frequency
    Frequency --> TimeUnit



```
```mermaid
---
title: Vehicle Management
---
classDiagram
class VehicleManagement {
<<Bounded Context>>
+VehicleService
+VehicleRepository
+VehicleFactory
}
class Vehicle {
<<Aggregate Root>>
String id
String licensePlate
VehicleStatus status
VehicleType type
Capacity capacity
Driver currentDriver
Route currentRoute
}
class VehicleStatus {
<<Value Object>>
StatusType type
DateTime lastUpdated
}
class StatusType {
<<Enumeration>>
OPERATIONAL
IN_MAINTENANCE
OUT_OF_SERVICE
}
class VehicleType {
<<Enumeration>>
BUS
TRAM
METRO
}
class Capacity {
<<Value Object>>
Integer seatedPassengers
Integer standingPassengers
}
class Driver {
<<Entity>>
String id
String name
String licenseNumber
ContactInfo contactInfo
}
class ContactInfo {
<<Value Object>>
String phoneNumber
String email
}

    VehicleManagement --> Vehicle
    Vehicle --> VehicleStatus
    Vehicle --> VehicleType
    Vehicle --> Capacity
    Vehicle --> Driver
    VehicleStatus --> StatusType
    Driver --> ContactInfo


```

```mermaid
---
title: Passenger Management
---
classDiagram
class PassengerManagement {
<<Bounded Context>>
+PassengerService
+PassengerRepository
+PassengerFactory
}
class Passenger {
<<Aggregate Root>>
String id
String name
ContactInfo contactInfo
List~Trip~ tripHistory
}
class ContactInfo {
<<Value Object>>
String email
String phoneNumber
}
class Trip {
<<Entity>>
String id
DateTime startTime
DateTime endTime
TripStatus status
Stop origin
Stop destination
Route route
Vehicle vehicle
Ticket ticket
}
class TripStatus {
<<Enumeration>>
SCHEDULED
IN_PROGRESS
COMPLETED
CANCELLED
}
class Ticket {
<<Value Object>>
String ticketNumber
BigDecimal price
TicketType type
}
class TicketType {
<<Enumeration>>
SINGLE_RIDE
DAY_PASS
MONTHLY_PASS
}

    PassengerManagement --> Passenger
    Passenger --> ContactInfo
    Passenger --> "0..*" Trip
    Trip --> TripStatus
    Trip --> Ticket
    Ticket --> TicketType

```

```mermaid
---
title: Real-Time Monitoring
---
classDiagram
class RealTimeMonitoring {
<<Bounded Context>>
+MonitoringService
+EventRepository
+AlertService
}
class VehicleStatus {
<<Aggregate Root>>
String vehicleId
Coordinates currentLocation
Integer passengerCount
Speed currentSpeed
Route currentRoute
DateTime lastUpdated
}
class Coordinates {
<<Value Object>>
Double latitude
Double longitude
}
class Speed {
<<Value Object>>
Double value
SpeedUnit unit
}
class SpeedUnit {
<<Enumeration>>
KMH
MPH
}
class MonitoringEvent {
<<Entity>>
String id
DateTime timestamp
EventType type
String description
VehicleStatus vehicleStatus
}
class EventType {
<<Enumeration>>
DELAY
BREAKDOWN
ROUTE_DEVIATION
TRAFFIC_CONGESTION
EMERGENCY
}
class Alert {
<<Entity>>
String id
AlertType type
String message
DateTime createdAt
List~String~ recipientIds
}
class AlertType {
<<Enumeration>>
SERVICE_DISRUPTION
SCHEDULE_CHANGE
EMERGENCY_NOTIFICATION
}

    RealTimeMonitoring --> VehicleStatus
    RealTimeMonitoring --> MonitoringEvent
    RealTimeMonitoring --> Alert
    VehicleStatus --> Coordinates
    VehicleStatus --> Speed
    Speed --> SpeedUnit
    MonitoringEvent --> EventType
    Alert --> AlertType

```

```mermaid
---
title: Notification Management
---
classDiagram
class NotificationManagement {
<<Bounded Context>>
+NotificationService
+NotificationRepository
+ChannelService
}
class Notification {
<<Aggregate Root>>
String id
String message
NotificationType type
DateTime createdAt
NotificationStatus status
List~NotificationChannel~ channels
}
class NotificationType {
<<Enumeration>>
SERVICE_UPDATE
DELAY_ALERT
EMERGENCY
PROMOTIONAL
}
class NotificationStatus {
<<Enumeration>>
PENDING
SENT
FAILED
}
class NotificationChannel {
<<Entity>>
String id
ChannelType type
String recipientAddress
}
class ChannelType {
<<Enumeration>>
EMAIL
SMS
PUSH_NOTIFICATION
}
class NotificationPreference {
<<Entity>>
String userId
Map~NotificationType, List~ChannelType~~ preferences
}

    NotificationManagement --> Notification
    NotificationManagement --> NotificationPreference
    Notification --> NotificationType
    Notification --> NotificationStatus
    Notification --> "1..*" NotificationChannel
    NotificationChannel --> ChannelType
    NotificationPreference --> NotificationType
    NotificationPreference --> ChannelType

```

---
title: Fare Management
---
classDiagram
class FareManagement {
<<Bounded Context>>
+FareService
+PaymentService
+FareCalculationService
}
class Fare {
<<Aggregate Root>>
String id
FareType type
BigDecimal basePrice
List~FareRule~ rules
}
class FareType {
<<Enumeration>>
SINGLE_RIDE
DAY_PASS
WEEK_PASS
MONTH_PASS
}
class FareRule {
<<Entity>>
String id
RuleType type
BigDecimal adjustment
}
class RuleType {
<<Enumeration>>
PEAK_HOUR
DISTANCE_BASED
AGE_DISCOUNT
BULK_PURCHASE
}
class Payment {
<<Entity>>
String id
BigDecimal amount
PaymentStatus status
PaymentMethod method
DateTime timestamp
}
class PaymentStatus {
<<Enumeration>>
PENDING
COMPLETED
FAILED
REFUNDED
}
class PaymentMethod {
<<Enumeration>>
CREDIT_CARD
DEBIT_CARD
MOBILE_WALLET
CASH
}

    FareManagement --> Fare
    FareManagement --> Payment
    Fare --> FareType
    Fare --> "1..*" FareRule
    FareRule --> RuleType
    Payment --> PaymentStatus
    Payment --> PaymentMethod



