docker buildx build --platform=linux/arm64,linux/amd64 -t jxch/capital-features:$(Get-Date -Format 'yyyyMMddHH') -t jxch/capital-features:latest . --push

# 第一次使用请先创建
# docker buildx create --use
