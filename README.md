# messages-lambda

Quarkus-based AWS Lambda function that listens to **DynamoDB Streams** events from the `messages-table` and publishes notifications to an **Amazon SNS** topic.

This project is used as a example of an asynchronous, event-driven system on AWS.

---

## Overview

The goal of this project is to demonstrate how to:

- React to **changes and TTL-based auto-deletions** in a DynamoDB table.
- Process change events with an **AWS Lambda** function written in Java using **Quarkus**.
- Forward processed events to an **SNS topic** for further fan-out to e-mail or other subscribers.
- Use **CloudWatch Logs** for observability.
- Keep **IAM permissions** for the Lambda function as small as possible.

---

## Architecture

```text
      Client
        │
        ▼
DynamoDB table: messages-table (TTL enabled)
        │
        │  DynamoDB Stream (INSERT, MODIFY, REMOVE incl. TTL expiration)
        ▼
AWS Lambda: messages-lambda (Quarkus)
        │
        │  publish message
        ▼
Amazon SNS topic
        │
        ├─ Email / SMS subscribers
        └─ Other services