<template>
	<div>
		<el-autocomplete
			class="inline-input"
			v-model="tempFileType"
			:fetch-suggestions="querySearch"
			placeholder="请选择文件类型"
			@input="handleInput"
			@select="handleSelect"
		></el-autocomplete>
	</div>
</template>
<script lang="ts">
	import { defineComponent, ref, toRefs } from 'vue';

	export default defineComponent({
		name: 'SearchInput',
		props: {
			handleInput: {
				type: Function,
				required: true
			},
			fileType: {
				type: String,
				required: true
			},
			fileTypes: {
				type: Array,
				required: true
			}
		},
		setup(props) {
			const { fileTypes, fileType } = toRefs(props);
			const tempFileType = ref(fileType.value)
			const querySearch = (queryString: string, cb) => {
				var results = queryString
					? fileTypes.value.filter(createFilter(queryString))
					: fileTypes.value;
				// 调用 callback 返回建议列表的数据
				cb(results);
			};
			const createFilter = (queryString: string) => {
				return (fileType) => {
					return (
						fileType.value.toLowerCase().indexOf(queryString.toLowerCase()) ===
						0
					);
				};
			};
			return {
				querySearch,
				createFilter,
				tempFileType
			};
		},
		methods: {
			handleSelect(e) {
				this.$emit('update:fileType', e.value);
			}
		}
	});
</script>
