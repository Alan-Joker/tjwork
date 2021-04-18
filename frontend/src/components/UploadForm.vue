<template>
	<div>
		<el-upload
			class="upload-demo"
			action="http://localhost:8080/tjwork_war_exploded/file/upload"
			:on-preview="handlePreview"
			:on-remove="handleRemove"
			:on-change="handleChange"
			:before-remove="beforeRemove"
			:before-upload="beforeUpload"
			:file-list="uploadFileList"
			:data="props"
			multiple
		>
			<el-button size="small" type="primary">点击上传</el-button>
		</el-upload>
	</div>
</template>

<script>
	import { defineComponent } from 'vue';
	export default defineComponent({
		name: 'UploadForm',
		props: {
			uploadFileList: {
				type: Array,
				default: []
			},
			props: {
				type: Object,
				default: {
					username: 'defaultUserName'
				}
			},
			handleRemove: {
				type: Function,
				default: () => {}
			},
			beforeRemove: {
				type: Function,
				default: () => {}
			},
			beforeUpload: {
				type: Function,
				default: () => {}
			},
			handlePreview: {
				type: Function,
				default: () => {}
			}
		},
		watch: {
			uploadFileList(v) {
				console.log('watch', v);
			}
		},
		methods: {
			handleChange(file, fileList) {
				this.$emit('update:uploadFileList', fileList.slice(-3));
			}
		}
	});
</script>
