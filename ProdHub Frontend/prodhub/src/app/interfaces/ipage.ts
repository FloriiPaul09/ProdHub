import { IRepo } from "./irepo";

export interface IPage {
  content : IRepo[],
  empty : boolean,
  first : boolean,
  last : boolean,
  number : number,
  numberOfElements : number,
  pageable : {offset : number, pageNumber : number, pageSize : number, paged : boolean}[],
  size : number,
  sort : {empty : boolean, sorted : boolean, unsorted : boolean}[],
  totalElements : number,
  totalPages : number
}
