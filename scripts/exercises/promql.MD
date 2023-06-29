container_cpu_usage_seconds_total{pod="microservice-person-69f4b5957f-gwscv"}

    rate(container_cpu_user_seconds_total{pod = "microservice-person-69f4b5957f-gwscv"}[1h]) * 100
//rate returns a per second value