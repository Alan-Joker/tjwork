import { request } from './index';
const baseUrl = '/file';
export const upload = async (payload: FormData) => {
	return await request.post(`${baseUrl}/upload`, payload);
};

export const searchByType = async (type: string) => {
	return await request.get(`${baseUrl}/serachFileBytype`, { params: { type } });
};

export const findAll = async (payload: { page: number; rows: number }) => {
	return await request.get(`${baseUrl}/findAll`, {
		params: payload
	});
};

export const downloadUrl = (filename: string, filePath: string) => {
	return `${request.defaults.baseURL}${baseUrl}/fileDownload?filename=${encodeURIComponent(
		filename
	)}&filePath=${encodeURIComponent(filePath)}`;
};
