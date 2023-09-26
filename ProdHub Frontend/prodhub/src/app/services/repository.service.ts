import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class RepositoryService {

  private apiRepo =  environment.apiReposPrivate;


  constructor(private http: HttpClient) { }

  upload(formData: FormData): Observable<HttpEvent<string[]>>{
    return this.http.post<string[]>(`${this.apiRepo}/upload `, formData, {
      reportProgress: true,
      observe: 'events',
    });
  }


  download(fileId: String): Observable<HttpEvent<Blob>>{
    return this.http.get(`${this.apiRepo}/download/${fileId}`,{
      reportProgress: true,
      observe: 'events',
      responseType: 'blob'
    });
  }


}
