<template>
	<div class="app-container">
		<div class="form-container">
			<SearchInput
				class="form-search"
				:handleInput="handleInput"
				:fileType.sync="fileType"
				:fileTypes="fileTypes"
			/>
			<el-input
				class="form-username"
				placeholder="请输入用户名"
				v-model="username"
			/>
			<UploadForm
				class="form-upload"
				:props="computedProps"
				:uploadFileList.sync="uploadFileList"
			/>
		</div>
		<div>
			<el-table :data="displayFileList" style="width: 100%">
				<el-table-column align="center" prop="id" label="ID" width="180" />
				<el-table-column
					align="center"
					prop="username"
					label="用户名"
					width="180"
				/>
				<el-table-column align="center" prop="filename" label="文件名" />
				<el-table-column align="center" prop="url" label="文件地址" />
				<el-table-column fixed="right" label="操作" width="100">
					<template #default="scope">
						<el-button
							@click="handleDownload(scope.row)"
							type="text"
							size="small"
							>下载</el-button
						>
					</template>
				</el-table-column>
			</el-table>
		</div>
		<div class="app-pagination" v-if="!fileType">
			<el-pagination
				layout="prev, pager, next"
				:total="total"
				:current-page.sync="page"
				:page-size.sync="rows"
				@current-change="handlePageChange"
			>
			</el-pagination>
		</div>
	</div>
</template>

<script lang="ts">
	import UploadForm from './components/UploadForm.vue';
	import SearchInput from './components/SearchInput.vue';
	import { defineComponent, onBeforeMount, onMounted, ref } from 'vue';
	import request from './request';

	import './index.scss';
	interface UploadFile {
		name: string;
		url: string;
	}
	interface DisplayFile {
		filename: string;
		username: string;
		url: string;
		count: number;
		type: string;
	}

	export default defineComponent({
		name: 'App',
		components: {
			UploadForm,
			SearchInput
		},
		setup(props) {
			const uploadFileList = ref([] as UploadFile[]);
			const displayFileList = ref([] as DisplayFile[]);
			const username = ref('');
			const fileType = ref('');
			const fileTypes = ['jpg', 'js', 'css', 'pdf', 'gif'].map((type) => ({
				value: type
			}));
			const page = ref(0);
			const rows = ref(10);
			const total = ref(0);
			return {
				uploadFileList,
				displayFileList,
				username,
				fileType,
				fileTypes,
				page,
				rows,
				total
			};
		},
		computed: {
			computedProps() {
				return {
					username: this.username
				};
			}
		},
		watch: {
			fileType: {
				handler(v) {
					if (!v) {
						this.findAll();
					} else {
						this.searchFile();
					}
				},
				immediate: true
			}
		},
		methods: {
			async searchFile() {
				const type = this.fileType;
				const { data } = await request.file.searchByType(type);
				if (data.code === 20000) {
					this.displayFileList = data.data.lists;
				}
			},
			async findAll() {
				const { page, rows } = this;
				const { data } = await request.file.findAll({
					page,
					rows
				});
				if (data.code === 20000) {
					this.total = data.data.total;
					this.displayFileList = data.data.fileList;
				}
			},
			handleInput(v) {
				this.fileType = v;
			},
			async handleDownload({ filename, url }) {
				const downloadUrl = await request.file.downloadUrl(filename, url);
				const a = document.createElement('a');
				a.href = downloadUrl;
				a.download = filename;
				a.click();
			},
			handlePageChange(page) {
				this.page = page;
				this.findAll();
			}
		}
	});
</script>

<style lang="scss" scoped>
	.app {
		&-container {
			padding: 100px;
		}
	}
	.form-container {
		display: flex;
		align-items: center;
		justify-content: center;
	}
	.form {
		&-username {
			display: flex;
			justify-content: center;
			margin-right: 10vw;
			max-width: 300px;
		}
		&-search {
			display: flex;
			justify-content: center;
			margin-right: 10vw;
		}
		&-upload {
			display: flex;
			justify-content: center;
		}
	}
</style>
