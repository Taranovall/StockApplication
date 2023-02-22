## stage 1
#FROM node:latest as node
#WORKDIR /app
#COPY . .
#RUN npm install
#RUN npm run build
#
## stage 2
#FROM nginx:alpine
#COPY --from=node ./nginx.conf /etc/nginx/conf.d/default.conf
#COPY --from=node ./dist/stocks-application-frontend /usr/share/nginx/html


# Stage 1: Compile and Build angular codebase

FROM node:latest as build
WORKDIR /usr/local/app
COPY ./ /usr/local/app/
RUN npm install
RUN npm run build


# Stage 2: Serve app with nginx server
FROM nginx:alpine

COPY --from=build /usr/local/app/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /usr/local/app/dist/stocks-application-frontend /usr/share/nginx/html
# Expose port 80
EXPOSE 80
