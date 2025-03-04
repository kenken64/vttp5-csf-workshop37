import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { FileuploadService } from '../services/fileupload.service';
import { db } from '../shared/app.db';
import { liveQuery } from 'dexie';
import { CityStore } from '../stores/city.store';
import { Observable } from 'rxjs';
import { City } from '../model/city';

@Component({
    selector: 'app-upload',
    templateUrl: './upload.component.html',
    styleUrl: './upload.component.css',
    standalone: false
})
export class UploadComponent implements OnInit{

  form!: FormGroup;
  dataUri!: string;
  blob!: Blob;
  //citiesList$:any;
  citiesList$!: Observable<City[]>;
  selectedCity!: string;

  constructor(private router:Router, private fb:FormBuilder, 
      private fileuploadService:FileuploadService, private cityStore: CityStore) {

  }

  ngOnInit(): void {
    this.createForm();
    this.loadCities();
  }

  onFileChange(event: Event){
    const input = event.target as HTMLInputElement;
    if(input.files && input.files.length > 0){
      const file = input.files[0];
      console.log(file);
      const reader = new FileReader();
      reader.onload = () => {
        this.dataUri = reader.result as string;
      };
      reader.readAsDataURL(file);
      console.log(this.dataUri);
    }
  }

  dataURItoBlob(dataURI: string): Blob{
    const [meta, base64Data] = dataURI.split(',');
    const mimeMatch = meta.match(/:(.*?);/);

    const mimeType = mimeMatch ? mimeMatch[1] : 'application/octet-stream';
    const byteString = atob(base64Data);
    const ab = new ArrayBuffer(byteString.length);
    const ia = new Uint8Array(ab);
    for(let i = 0; i < byteString.length; i++){
      ia[i] = byteString.charCodeAt(i);
    }
    return new Blob([ia], {type: mimeType});
  }

  upload(){
    console.log("upload an image");
    console.log(this.dataUri);
    if(!this.dataUri){
      return;
    }
    this.blob = this.dataURItoBlob(this.dataUri);
    const formValue = this.form.value;
    this.fileuploadService.upload(formValue, this.blob).then((result) => {
      console.log(result);
      this.router.navigate(['/image', result.postId]);
    });
  }

  createForm(){
    this.form = this.fb.group({
      comments: this.fb.control<string>('')
    });
  }

  loadCities(){
    // connect to the selector
    this.citiesList$ = this.cityStore.cities$;
    //load the cities
    // Initiate loading of todos
    this.cityStore.loadCities();
    console.log(this.citiesList$)
  }

}
