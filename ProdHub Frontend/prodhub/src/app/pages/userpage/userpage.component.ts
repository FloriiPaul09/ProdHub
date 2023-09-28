import { HttpErrorResponse, HttpEvent, HttpEventType } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { RepositoryService } from 'src/app/services/repository.service';
import { UserService } from 'src/app/services/user.service';
import { saveAs } from 'file-saver';
@Component({
  selector: 'app-userpage',
  templateUrl: './userpage.component.html',
  styleUrls: ['./userpage.component.scss']
})
export class UserpageComponent implements OnInit{

  public: boolean = true;
  button: boolean = false;

  username : string = '';
  filenames: string[] = [];
  fileStatus = {status: '', requestType:'', percent: 0};

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

  onUploadFiles(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    const files = inputElement.files;

    if (files) {
      const fileList: File[] = Array.from(files);

      if (fileList.length > 0) {
        const formData = new FormData();
        for (const file of fileList) {
          formData.append('files', file, file.name);
        }
        this.repositorySrvc.upload(formData).subscribe(
          (httpEvent: HttpEvent<any>) => {
            console.log(httpEvent);
            this.reportProgress(httpEvent);
          },
          (error: HttpErrorResponse) => {
            console.log(error);
          }
        );
      }
    }
  }

  onDownloadFile(filename: string): void {
    this.repositorySrvc.download(filename).subscribe(
      (httpEvent: HttpEvent<any>) => {
        console.log(httpEvent);
        this.reportProgress(httpEvent);
      },
      (error: HttpErrorResponse) => {
        console.log(error);
      }
    );
  }



  private reportProgress(httpEvent: HttpEvent<string[]|Blob>): void {
    switch(httpEvent.type) {
      case HttpEventType.UploadProgress:
        this.updateStatus(httpEvent.loaded, httpEvent.total!, 'Uploading... ');
        break;
      case HttpEventType.DownloadProgress:
        this.updateStatus(httpEvent.loaded, httpEvent.total!, 'Downloading... ');
        break;
      case HttpEventType.ResponseHeader:
        console.log('Header returned', httpEvent);
        break;
      case HttpEventType.Response:
        if (httpEvent.body instanceof Array) {
          this.fileStatus.status = 'done';
          for (const filename of httpEvent.body) {
            this.filenames.unshift(filename);
          }
        } else {
          saveAs(new File([httpEvent.body!], httpEvent.headers.get('fileId')!,
                  {type: `${httpEvent.headers.get('Content-Type')};charset=utf-8`}));

        }
        this.fileStatus.status = 'done';
        break;
        default:
          console.log(httpEvent);
          break;

    }
  }
  updateStatus(loaded: number, total: number, requestType: string) {
    this.fileStatus.status = 'progress';
    this.fileStatus.requestType = requestType;
    this.fileStatus.percent = Math.round(100 * loaded / total);
  }

}
