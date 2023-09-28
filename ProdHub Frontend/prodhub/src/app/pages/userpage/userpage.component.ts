import { HttpErrorResponse, HttpEvent, HttpEventType } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { RepositoryService } from 'src/app/services/repository.service';
import { UserService } from 'src/app/services/user.service';
import { IPage } from 'src/app/interfaces/ipage';
@Component({
  selector: 'app-userpage',
  templateUrl: './userpage.component.html',
  styleUrls: ['./userpage.component.scss']
})
export class UserpageComponent implements OnInit{

  prData: IPage = {
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

  pubData: IPage = {
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


  publicOrPrivate: boolean = true;
  public: boolean = true;
  button: boolean = false;

  username : string = '';

  fileId: string = '';
  selectedFile : File | null = null;

  constructor(private userSrvc : UserService,
              private repositorySrvc : RepositoryService
              ){}

  ngOnInit(){
    //get all public and private posts
    this.repositorySrvc.getPublicRepo().subscribe((pubData: any) =>{
      if(Array.isArray(pubData.content)){
      this.pubData.content = pubData.content;
      console.log(pubData);
    }
    },
    (error) => {
      console.error("Errore durante l'operazione di recupero", error);
    });

    this.repositorySrvc.getPrivateRepo().subscribe((prData: any) =>{
      if(Array.isArray(prData.content)){
      this.prData.content = prData.content;
      console.log(prData);
    }
    },
    (error) => {
      console.error("Errore durante l'operazione di recupero", error);
    });

    //user name display
    this.userSrvc.isUserLogged.subscribe((user) =>{
      if(user) {
        this.username = user.username;
      }
    });

  }




  onSelectFile(event : any){
    this.selectedFile = event.target.files[0];
  }

  uploadPrivate(){

    if(!this.selectedFile){
      console.log('There is no file selected...');
      return;
    }

    const formData = new FormData();
    formData.append('file', this.selectedFile);

    this.repositorySrvc.uploadPrivate(formData).subscribe((event:HttpEvent<string[]>) => {
      if(event.type === HttpEventType.Response){
        console.log('File uploaded on private database successfully', event.body);
      }
    },
    (error) => {
      console.log('Error in the upload....', error);
    }
    )

  }

  uploadPublic(){

    if(!this.selectedFile){
      console.log('There is no file selected...');
      return;
    }

    const formData = new FormData();
    formData.append('file', this.selectedFile);

    this.repositorySrvc.uploadPublic(formData).subscribe((event:HttpEvent<string[]>) => {
      if(event.type === HttpEventType.Response){
        console.log('File uploaded on public database successfully', event.body);
      }
    },
    (error) => {
      console.log('Error in the upload....', error);
    }
    )

  }

  downloadPublic(fileId : string){}

  downloadPrivate(fileId : string){}

}
