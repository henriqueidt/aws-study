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

## Storage Management

S3 Batch Operations can do the following:

- Copy objects between buckets
- Replace objects tag sets
- Modify access controls
- Restore archived objects from Amazon S3 Glacier

## Version control

- Prevents accidental deletion
- Replicate data to the same or different AWS Regions
