# FROM node:alpine
# RUN apk update
# WORKDIR /saraFront/usr/dist/app
# COPY . /saraFront/usr/dist/app
# RUN npm install -g @angular/cli
# RUN npm install
# CMD ["ng", "serve", "--host", "0.0.0.0"]

FROM node:alpine
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN npm install -g @angular/cli
RUN npm install
ENV PATH /usr/local/bin:$PATH
CMD ["ng", "serve", "--host", "0.0.0.0"]


