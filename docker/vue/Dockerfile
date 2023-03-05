FROM node:12
EXPOSE 8082
WORKDIR /app

COPY ../../vue/business_work_front/business_work_front/package.json ./

RUN npm install

CMD ["npm", "install", "--save", "@babel/polyfill"]
COPY ../../vue/business_work_front/business_work_front/ ./

CMD ["npm", "run", "serve"]
VOLUME ./vue/business_work_front/business_work_front ./app
