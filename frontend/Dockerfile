# Frontend Dockerfile

FROM node:lts-alpine

WORKDIR /app

COPY . .

RUN npm install -g http-server

EXPOSE 8081

CMD ["http-server", "-p", "8081", "--cors"]
