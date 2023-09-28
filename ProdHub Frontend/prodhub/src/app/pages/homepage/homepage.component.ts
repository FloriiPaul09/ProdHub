import { Component, OnInit } from '@angular/core';
import { RepositoryService } from 'src/app/services/repository.service';
@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit{

  files: any[] = [];

  constructor(public repositorySrvc: RepositoryService){}

  ngOnInit(): void {
    this.repositorySrvc.getPublicRepo().subscribe((data) =>{
      this.files = data;
    })
  }

}
