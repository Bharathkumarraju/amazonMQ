aws --region us-west-1 logs put-resource-policy --policy-name AmazonMQ-Logs --policy-document '{"Version":"2012-10-17","Statement":[{"Effect":"Allow","Principal":{"Service":"mq.amazonaws.com"},"Action":["logs:PutLogEvents","logs:CreateLogStream"],"Resource":"arn:aws:logs:*:*:log-group:/aws/amazonmq/*"}]}'




aws --region ap-southeast-1 logs put-resource-policy --policy-name AmazonMQ-Logs-staging --policy-document '{"Version":"2012-10-17","Statement":[{"Effect":"Allow","Principal":{"Service":"mq.amazonaws.com"},"Action":["logs:PutLogEvents","logs:CreateLogStream"],"Resource":"arn:aws:logs:*:*:log-group:/aws/amazonmq/*"}]}'


bharathkumardasaraju@external$ aws-vault exec aws-staging -- aws --region ap-southeast-1 logs put-resource-policy --policy-name AmazonMQ-Logs-staging --policy-document '{"Version":"2012-10-17","Statement":[{"Effect":"Allow","Principal":{"Service":"mq.amazonaws.com"},"Action":["logs:PutLogEvents","logs:CreateLogStream"],"Resource":"arn:aws:logs:*:*:log-group:/aws/amazonmq/*"}]}'

{
    "resourcePolicy": {
        "policyName": "AmazonMQ-Logs-staging",
        "policyDocument": "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"Service\":\"mq.amazonaws.com\"},\"Action\":[\"logs:PutLogEvents\",\"logs:CreateLogStream\"],\"Resource\":\"arn:aws:logs:*:*:log-group:/aws/amazonmq/*\"}]}",
        "lastUpdatedTime": 1722118213743
    }
}
bharathkumardasaraju@external$