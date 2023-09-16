import { Injectable } from '@angular/core';
import { IRepos } from '../interfaces/irepos';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class ReposService {

  apiUrl = environment.apiRepos;

  constructor(
    private http: HttpClient
  ) { }

  getAllRepos(){
    return this.http.get<IRepos[]>(this.apiUrl);
  }


  getRepoById(id:number){
    return this.http.get<IRepos>(`${this.apiUrl}/${id}`);
  }

  postRepo(data:Partial<IRepos>){
    return this.http.post<IRepos>(`${this.apiUrl}`, data);
  }

  putRepo(data:Partial<IRepos>){
    return this.http.put<IRepos>(`${this.apiUrl}/${data.id}`, data);
  }

  deleteRepo(id:number){
    return this.http.delete<IRepos>(`${this.apiUrl}/${id}`);
  }

}
