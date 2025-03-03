import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { UploadResult } from '../model/upload-result';
import { City } from '../model/city';

@Injectable({
  providedIn: 'root'
})
export class CitiesService {

  constructor(private httpClient: HttpClient) { }

  

  getCities(){
    return lastValueFrom(this.httpClient.get<City[]>(`/api/cities`));
  }
}
