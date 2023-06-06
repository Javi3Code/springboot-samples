[[ "${$(docker images | awk '{print $1}')[*]}" =~ "red-pos-sam" ]] && {
  echo "deleting old image"
  docker rmi red-pos-sam:latest
}
[[ "${$(docker volume ls | awk '{print $2}')[*]}" =~ "rediscache-postgressql-sample_postgres-data" ]] &&
{
  docker volume rm rediscache-postgressql-sample_postgres-data
}
docker build . -t red-pos-sam
