import { HttpEventType } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { RepositoryService } from 'src/app/services/repository.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-userpage',
  templateUrl: './userpage.component.html',
  styleUrls: ['./userpage.component.scss']
})
export class UserpageComponent implements OnInit{



  username : string = '';

  constructor(private userSrvc : UserService,
              private repositorySrvc : RepositoryService
              ){}

  ngOnInit(){
    this.userSrvc.isUserLogged.subscribe((user) =>{
      if(user) {
        this.username = user.username;
      }
    });
  }

  fileSelect(event : any){
    const selectedFile = event.target.files[0];
  if (selectedFile) {
    this.repositorySrvc.upload(selectedFile).subscribe(
      (event) => {
        if (event.type === HttpEventType.UploadProgress) {
          const total = event.total;
          if (total !== undefined) {
            const percentDone = Math.round((100 * event.loaded) / total);
            console.log(`File is ${percentDone}% uploaded.`);
          } else {
            console.log('File upload progress: Total size unknown.');
          }
        } else if (event.type === HttpEventType.Response) {
          console.log('File upload complete:', event.body);
        }
      },
      (error) => {
        console.error('File upload error:', error);
      }
    );
  }
  }

  getfiles(){
    this.repositorySrvc.getFiles().subscribe(
      (files) => {
        console.log('Files: ', files);
      },
      (error) => {
        console.log('Error fetching the files: ', error);
      }
    );
  }

}
