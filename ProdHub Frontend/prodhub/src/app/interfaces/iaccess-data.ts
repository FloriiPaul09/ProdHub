export interface IAccessData {
  accessToken : string;
  tokenType : string;
  username : string;
  roles:{id : number, roles : string}[]
}
