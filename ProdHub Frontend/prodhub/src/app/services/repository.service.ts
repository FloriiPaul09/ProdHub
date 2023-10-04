import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class RepositoryService {

  private apiRepoPrivate =  environment.apiReposPrivate;
  private apiRepoPublic = environment.apiReposPublic;

  constructor(private http: HttpClient) { }

  //upload methods

  uploadPrivate(formData: FormData): Observable<HttpEvent<string[]>>{
    return this.http.post<string[]>(`${this.apiRepoPrivate}/upload `, formData, {
      reportProgress: true,
      observe: 'events',
    });
  }

  uploadPublic(formData: FormData): Observable<HttpEvent<string[]>>{
    return this.http.post<string[]>(`${this.apiRepoPublic}/upload `, formData, {
      reportProgress: true,
      observe: 'events',
    });
  }

  //download methods

  downloadPrivate(fileId: String): Observable<HttpEvent<Blob>>{
    return this.http.get(`${this.apiRepoPrivate}/download/${fileId}`,{
      observe: 'events',
      responseType: 'blob'
    });
  }

  downloadPublic(): Observable<string>{
    const url = `${this.apiRepoPublic}/download`;
    return this.http.get<string>(url);
  }


  //All post methods

  getPublicRepo(): Observable<any[]>{
    return this.http.get<any[]>(`${this.apiRepoPublic}/upload`);
  }

  getPrivateRepo(): Observable<any[]>{
    return this.http.get<any[]>(`${this.apiRepoPrivate}/upload`);
  }

}
