import { Component, OnInit } from '@angular/core';
import { IPage } from 'src/app/interfaces/ipage';
import { IRepo } from 'src/app/interfaces/irepo';
import { RepositoryService } from 'src/app/services/repository.service';
@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit{

  data: IPage = {
    content: [],
    empty: false,
    first: false,
    last: false,
    number: 0,
    numberOfElements: 0,
    pageable: [],
    size: 0,
    sort: [],
    totalElements: 0,
    totalPages: 0
  };

  constructor(public repositorySrvc: RepositoryService){}

  ngOnInit(): void {
    this.repositorySrvc.getPublicRepo().subscribe((data: any) =>{
      if(Array.isArray(data.content)){
      this.data.content = data.content;
      console.log(data);
    }
    },
    (error) => {
      console.error("Errore durante l'operazione di recupero", error);
    });
  }

}
