import {CoordinatesDto} from "./coordinates-dto";

export interface GpxdataDto {
  id?: number | null,
  name?: string | null,
  coords?: CoordinatesDto[] | null
}
