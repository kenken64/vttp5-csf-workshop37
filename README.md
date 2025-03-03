## Workshop 37 

#### Client 

```
ng new --standalone=false client

ng g c --flat --skip-tests components/upload

ng g c --flat --skip-tests components/view-image

ng g s --flat --skip-tests services/fileupload

ng g i model/UploadResult

ng serve --proxy-config proxy-config.js

```

```
npm i dexie
```

#### Server 

```
mvn clean spring-boot:run
```