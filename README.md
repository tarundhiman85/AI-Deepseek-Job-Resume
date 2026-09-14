# DeepSeek Job Finder

A Spring Boot service that turns scraped LinkedIn job postings into AI-tailored resumes. It listens for job data on Kafka, scrapes the full job description, feeds it — along with a candidate's base resume — to a locally-hosted **DeepSeek** model via **Ollama**, and publishes a generated, resume tailored to that job back onto Kafka as structured HTML.

## How it works

```
Job scraper (external)          DeepSeekJobFinder
        │                               │
        ▼                               ▼
  Kafka topic:               1. JobConsumer batches
  "LinkedinJobs"    ─────►      incoming JobData (batch size 3)
                                        │
                                        ▼
                               2. JobProcessor:
                                  - filters out junk/invalid postings
                                  - scrapes the job link with Jsoup
                                    to pull the requirements section
                                  - builds a prompt from the
                                    candidate's resume + requirements
                                  - calls the local DeepSeek model
                                    (via Spring AI + Ollama) to
                                    generate a tailored HTML resume
                                  - strips <think> reasoning tags
                                  - minifies the HTML
                                        │
                                        ▼
                               3. Publishes {title, link, resume,
                                  requirements} as JSON to Kafka
                                  topic "jobTopic"
```

> **Note:** This repo contains only the *consumer/processor* side of the pipeline. Something upstream (e.g. a scraper or browser extension) needs to publish `JobData` messages to the `LinkedinJobs` topic for this service to have anything to process.

## Tech stack

- **Java 17**, **Spring Boot 3.4.2**
- **Spring AI** (`spring-ai-ollama`) — talks to a local **Ollama** server running `deepseek-r1:7b`
- **Spring Kafka** — consumes raw job postings, produces tailored results
- **Jsoup** — scrapes job requirement details from the job posting URL
- **Gson** / **Jackson** — JSON (de)serialization
- **Lombok**

## Project structure

```
src/main/java/com/tarun/DeepSeekJobFinder/
├── DeepSeekJobFinderApplication.java   # Spring Boot entry point
├── config/
│   ├── KafkaConsumerConfig.java        # consumer factory / listener container
│   └── KafkaProducerConfig.java        # producer factory / KafkaTemplate
├── constants/
│   └── Constants.java                  # JobApplicant enum (candidate profiles)
├── model/
│   └── JobData.java                    # incoming job payload (id, company, position, location, description, link)
├── service/
│   ├── JobConsumer.java                # listens on "LinkedinJobs", batches, delegates to JobProcessor
│   └── JobProcessor.java               # scrapes + prompts the LLM to build a tailored resume
└── utils/
    └── Utils.java                      # HTML scraping/cleanup, resume templates, <think>-tag stripping

kafka-zookeeper.yml                     # docker-compose for local Kafka + Zookeeper
```

## Prerequisites

- Java 17+
- Maven (or use the bundled `./mvnw`)
- Docker (for Kafka + Zookeeper)
- [Ollama](https://ollama.com) installed locally, with the `deepseek-r1:7b` model pulled:
  ```bash
  ollama pull deepseek-r1:7b
  ollama serve
  ```

## Running it

1. **Start Kafka + Zookeeper:**
   ```bash
   docker compose -f kafka-zookeeper.yml up -d
   ```
2. **Make sure Ollama is running** on `http://localhost:11434` with `deepseek-r1:7b` available.
3. **Run the app:**
   ```bash
   ./mvnw spring-boot:run
   ```
4. **Feed it jobs:** publish `JobData` JSON messages to the `LinkedinJobs` Kafka topic (from an external scraper or manually via a producer console). Once 3 messages have accumulated, they're processed and the results are published to the `jobTopic` topic.

## Configuration

Key settings live in `src/main/resources/application.properties`:

| Property | Purpose |
|---|---|
| `spring.ai.ollama.base-url` | URL of the local Ollama server (default `http://localhost:11434`) |
| `spring.ai.ollama.chat.model` | Model used for generation (`deepseek-r1:7b`) |

Kafka bootstrap servers are currently set directly in `KafkaConsumerConfig` / `KafkaProducerConfig` (`localhost:9092`) rather than via `application.properties`.

## Candidate resumes

`Utils.getResumeText(name)` holds hardcoded base resumes for three sample candidates (`Tarun`, `Parth`, `Karthik`), defined in the `Constants.JobApplicant` enum. `JobProcessor` currently always generates resumes for **Karthik** — swap the enum value passed in `JobProcessor.processJob` (or extend `JobData` to carry a candidate selector) to generate resumes for a different profile or plug in your own.

## Known limitations

- The job-requirements scraper (`Utils.fetchJobDetails`) assumes a fixed HTML structure (it grabs the 4th `<ul>` on the page), so it's tuned to a specific job board layout and may break on other sites.
- Resume candidates and Kafka connection details are hardcoded rather than externally configurable.
- No REST API — this is a headless Kafka consumer/producer service.

## License

No license file is currently included in this repository.
