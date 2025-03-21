# Amazon S3

## Primary Storage Types

### Block Storage

- Basic fixed storage units in the device

### Object Storage (AWS S3)

- Built on top of Block Storage
- Created with an operating system that formats and manages read/write of the Block Storage
- Does not differentiate between types of data
- Made up of a large number of Storage Blocks, using a predetermined object size

### File Storage

- Built on top of Block Storage
- Usually serves as a file share or file server
- Created with an operating system that formats and manages read/write of the Block Storage
- Stores data as files tipically in a directory-tree hierarchy

## About S3

- Data is stored as objects into buckets
- An object is composed by a file and optionally metadata that describes the file
- While uploading a file to S3, it is possbile to define permissions and any metadata to the file
- Buckets store objects. It is possible to control access to them, get access logs and chose the region where it will be stored
- An object can have up to 5 terabytes in size
- All objects inside a bucket are organized in a flat way. You can use prefixes to organize, but it will not add any hierarchy

## Analytics

### Storage Management

S3 Batch Operations can do the following:

- Copy objects between buckets
- Replace objects tag sets
- Modify access controls
- Restore archived objects from Amazon S3 Glacier

### Version control

- Prevents accidental deletion
- Provides preservation, retrieve, and restore of every version

### Replication

- Possible to replicate objects and their metadata into other buckets in the same or different AWS regions
- **S3 Cross-Region Replication**
- **S3 Same-Region Replication**

### Retention and compliance

- **S3 Object Lock** lets you block an object version from deletion
- Write once, read many (WORM) compliance possibility - Data cannot be changed or deleted after written

### Monitoring

- **AWS Cost Allocation Reports** can be used to monitor usage and costs per tag
- **Amazon CloudWatch** can be configured to send billing alerts and track health
- **AWS CloudTrail** montior and report bucket and object level activities
- **S3 Event Notifications** can be used to trigger workflows, alerts, and invoke Lambda when some change is made to S3. It can:
  - Transcode media files as they are uploaded
  - Process data files as they become available
  - Sync objects with other data stores
- **S3 Storage Lens** provides insights on object usage and trends, and also recommendations to optimize the storage
- **S3 Storage Class Analysis** analyzes access patterns to help transition less frequent accessed data to lower-cost storaging

## Access Management & Security

### Access Management

- By default, users only have access to resources they create
- **S3 Block Public Access** blocks public access to buckets and objects by default
- **Access Analyzer for Amazon S3** monitors bucket access policies
- **Amazon Macie** can discover and protect any sensitive data in S3. It can use ML to detect any sensitive data, like PII and alert you. It can provide alerts on:
  - Public accessible bucket
  - Unencrypted bucket
  - Buckets shared or replicated with accounts out of your organization
- Access can be granted by an admin via:
  - **IAM** create users and manage their permissions
  - **Access control** lists make inividual objects accessible by authorized users
  - **Bucket policies** configure permissions for all objects inside a bucket
  - **S3 Access Points** with names and permissions specific to each application or sets of applications
  - **Query String Authentication** grants time-limited access with temporary URLs

### On-premises connectivity

- **Amazon Virtual Private Cloud (VPC)** can be used to connect on-premises environment with Amazon S3
- **AWS PrivateLink** provides private connectivity between S3 and on-premises environment

### Encryption

- By default, all objects are encrypted with Amazon S3 managed keys (SSE-S3) or AWS Key Management Service (AWS KMS) keys stored in AWS KMS (SSE-KMS)

## Data processing

### S3 Object Lambda

- Uses Lambda functions to process the output of S3 before sending to the consumer

### S3 Select

- Increases performance by smartly querying a subset of an object, instead of the entire object when retrieving data

## Pricing

AWS S3 has 6 pricing components:

- Storage pricing
- Request and data retrieval pricing
- Data transfer and acceleration pricing
- Data management and analytics pricing
- S3 Object Lambda processing pricing
- S3 Region pricing

## ARCHITECTURE

- S3 is a regional service. When creating a bucket you specify the Region. S3 utilizes the Availability Zones in that region to host the bucket
- Access Points in the Region of the bucket allow you to create multiple logical points of entry to the Bucket.
- Access Points can be used to specify a prefix level a user can access
- Multi-Region Access Points can grant access to multiple region buckets with one access point
- Each bucket has a globally unique name across AWS
- You can use cross-region replication between S3 buckets from different regions
- AWS Outposts can be configured to include S3 storage
- S3 uses REST APIs to exchange information between applications

![S3 Architecture](./assets/s3-architecture.png)

### S3 Intelligent Tiering Storage Class

- Moves data to the most cost-efficient access tier based on access frequency

![Intelligent Tiering](./assets/intelligent-tiering.jpg)

### S3 Lifecycle

- Automate transfer data to different storage class without changes to application
- Transition actions - define when objects transition from one storage class to another
- Expiration actions - define when objects expire and Amazon can delete

## STORAGE CLASSES

- S3 mantains durability by copying an object across multiple Availability Zones (AZ) within a single AWS Region
- 99.999999999% (eleven nines) of durability
- Minimum of 3 AZs in a region for one data
- Each AZ is separated by a min of 1km and max 100km to avoid natural disaster to kill all data

### Amazon S3 Standard (general purpose)

- Default assigned class for objects if not specified
- Best suitable for frequently accessed data
- Resilient in the event of one entire Availability Zone destruction
- Low latency
- No minimum duration
- No minimum size
- No retrieval fees

|         Tier         | Amount stored |     Price     |
| :------------------: | :-----------: | :-----------: |
| Frequent Access Tier |  Up to 50 TB  | $0.023 per GB |
| Frequent Access Tier | 50+ to 450 TB | $0.022 per GB |
| Frequent Access Tier |  Over 500 TB  | $0.021 per GB |

### Amazon S3 Standard-Infrequent Access (S3 Standard-IA)

- Not frequently accessed, but still requires rapid access when needed
- Stores objects redundantly across multiple Availability Zones
- Minimum size of 128 KB (if less than that is stored, the 128K KB fee will be charged)
- Minimum duration of 30 days (if stored for less than that, it will be charged for 30 days)
- Same low latency and performanse of S3 Standard
- Resilient in the event of one entire Availability Zone destruction

### Amazon S3 One Zone-Infrequent Access (S3 One Zone-IA)

- Not frequently accessed, but still requires rapid access when needed
- Stores the data in only one Availability Zone (risk of data loss if a natural disaster occurs)
- Costs 20% less than S3 Standard-IA
- All other characteristics of S3 Standard-IA

| Storage class  |         Tier          |         Amount Stored         |     Price      |
| :------------: | :-------------------: | :---------------------------: | :------------: |
| S3 Standard-IA | Infrequently accessed | Total amount of storage/month | $0.0125 per GB |
| S3 One Zone-IA | Infrequently accessed | Total amount of storage/month |  $0.01 per GB  |

### Amazon S3 Intelligent-Tiering

- Optimizes costs by automatically moving data to the most cost-efficient access tier
- Best suitable for unknown or changing access patterns
- Monthly object monitoring and automation fee
- Best suitable for data lakes, big data and media applications
- Objects with less than 128 KB will always go to **Frequent Access Tier**
- Objects uploaded automatically go to **Frequent Access Tier**
- Objects not accessed for 30 days go to **Infrequent Access Tier**
- Objects not accessed for 90 days go to **Archive Access Tier** (needs to be activated)
- Objects not accessed for 180 days go to **Deep Archive Access Tier** (needs to be activated)
- Objects accesed afterwards will be moved automatically back to the **Frequent Access Tier**

![Intelligent Tiering 2](./assets/intelligent-tiering-2.png)

|           Tier           |    Amount stored    |      Price      |
| :----------------------: | :-----------------: | :-------------: |
|   Frequent Access Tier   |  First 50 TB/month  |  $0.023 per GB  |
|   Frequent Access Tier   |  Next 450 TB/month  |  $0.022 per GB  |
|   Frequent Access Tier   |  Over 500 TB/month  |  $0.021 per GB  |
|  Infrequent Access Tier  | All Storage / Month | $0.0125 per GB  |
|   Archive Access Tier    | All Storage / Month |  $0.004 per GB  |
| Deep Archive Access Tier | All Storage / Month | $0.00099 per GB |

### Amazon S3 Glacier Instant Retrieval

- Lowest cost for long-lived rarely accessed data with milliseconds retrieval
- Redundantly stores data across multiple phisically separated AWS AZs
- Instant access like S3 Standard
- Minimum duration of 90 days (if stored for less than that, it will be charged for 90 days)
- Minimum size of 128 KB (if less than that is stored, the 128 KB fee will be charged)

### Amazon S3 Glacier Flexible Retrieval (S3 Glacier)

- Best suitable for data accessed 1-2 times a year
- Configurable Retrieval times from minutes to hours
  - Expedited (1–5 mins)
  - Standard (3–5 hours)
  - Bulk (5-12 hours)
- Minimum duration of 90 days (if stored for less than that, an early deletion fee will be charged)
- Minimum size of 40 KB (if less than that is stored, the 40 KB fee will be charged)

### Amazon S3 Glacier Deep Archive (S3 Glacier Deep Archive)

- Lowest cost storage in AWS S3
- Best suitable for data accessed 1-2 times a year
- Designed for long-term retation of data 7-10 years
- Retrieval time is within 12 hours
- Minimum duration of 180 days (if stored for less than that, it will be charged for 180 days)
- Minimum size of 40 KB (if less than that is stored, the 40 KB fee will be charged)

|         Storage class         |                                                Storage Class                                                 |         Amount stored         |      Price      |
| :---------------------------: | :----------------------------------------------------------------------------------------------------------: | :---------------------------: | :-------------: |
| S3 Glacier Instant Retrieval  |          For long-lived archive data accessed once a quarter with instant retrieval in milliseconds          | Total amount of storage/month |  $0.004 per GB  |
| S3 Glacier Flexible Retrieval |              For long-term backups and archives with retrieval option from 1 minute to 12 hours              | Total amount of storage/month | $0.0036 per GB  |
|    S3 Glacier Deep Archive    | For long-term data archiving that is accessed once or twice in a year and can be restored within 12-48 hours | Total amount of storage/month | $0.00099 per GB |

![Classes Comparison](./assets/s3-classes-comparison.jpg)
