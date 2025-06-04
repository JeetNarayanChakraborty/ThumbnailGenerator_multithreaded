# Generate Thumbnail - Multithreaded

This project is a Java and Spring Boot-based API that demonstrates efficient and scalable image thumbnail generation using multithreading. Built for high performance and real-time processing, the application can handle simultaneous API calls for generating thumbnails from multiple image URLs.

## Features

* **Multi-threaded Processing**: Efficient use of Java’s concurrency tools to handle multiple image download and resize operations in parallel.
* **Dynamic Thread Pool Scaling**: Thread pool size adjusts dynamically based on user traffic and API call rate.
* **REST API Endpoint**: Simple POST API that accepts a list of image URLs and creates and stores their resized Base64 thumbnails.

## Technical Highlights

### 1. ExecutorService with Dynamic Scaling

```java
ThreadPoolExecutor executor = new ThreadPoolExecutor(
    corePoolSize, maxPoolSize,
    keepAliveTime, TimeUnit.SECONDS,
    new LinkedBlockingQueue<>());
```

The pool dynamically resizes based on runtime traffic and load.

### 2. Dynamic Thread Pool Adjustment

```@Scheduled(fixedRate = 9999)
public void adjustPoolSize() 
{
    int recentLoad = apiCallCounterService.getApiCallCount(); // Get the recent API call count from the last 10 seconds

    if(recentLoad > 100) 
    {
        executor.setMaximumPoolSize(8);   // Dynamically adjust the maximum pool size based on the load
        executor.setCorePoolSize(6);
    }
    else if(recentLoad < 20) 
    {
        executor.setMaximumPoolSize(4);
        executor.setCorePoolSize(2);
    }
}
```

This logic periodically monitors API traffic and adjusts the thread pool size accordingly.


## How to Run

1. **Clone the Repository**

```bash
git clone <repo-url>
cd GenarateThumbnail
```

2. **Build the Project**

```bash
mvn clean install
```

3. **Run the Application**

```bash
java -jar target/generate-thumbnail-0.0.1-SNAPSHOT.jar
```

---

For any questions or feature requests, feel free to open an issue.
