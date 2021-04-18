import axios from 'axios'
import * as file from './fileRequest'
axios.defaults.baseURL = 'http://localhost:8080/tjwork_war_exploded'

export const request = axios

export default {
  file
}