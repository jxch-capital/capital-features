docker buildx build --platform=linux/arm64,linux/amd64 -t jxch/capital-features:$(Get-Date -Format 'yyyyMMddHH') -t jxch/capital-features:latest . --push
